package com.credence.app.data



import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


// Model for the active chat response
data class ChatResponse(
    val reply: String
)

// Model for the finalized clinical report
data class FinalReportResponse(
    val report: String
)

// Model for the Timeline Ledger
data class LogResponse(
    val id: Long,
    val date: String,
    val clinicalReport: String
)

// Model for the Zero-Trust QR Handoff
data class ShareResponse(
    val shareUrl: String,
    val pin: String,
    val expiresAt: String
)


interface ApiService {
    @POST("/api/chat/message")
    suspend fun sendChatMessage(@Body payload: Map<String, Any>): ChatResponse

    @POST("/api/chat/complete")
    suspend fun completeSession(@Body payload: Map<String, Any>): FinalReportResponse

    @GET("/api/logs/all")
    suspend fun getAllLogs(): List<LogResponse>

    @POST("/api/share")
    suspend fun generateShareLink(@Body payload: Map<String, List<Long>>): ShareResponse
}


class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val responseString: String


        when {
            uri.endsWith("/api/chat/message") -> {
                responseString = """
                    {
                      "reply": "I'm so sorry to hear that you're experiencing chest pain. That can be really scary and uncomfortable. I can see that your heart rate is a bit elevated at 92, which might be related to the discomfort you're feeling. Can you tell me more about the pain, like when it happens and how it feels?"
                    }
                """.trimIndent()
            }
            uri.endsWith("/api/chat/complete") -> {
                responseString = """
                    {
                      "report": "**Medical Intake Report**\n\n**Chief Complaint:** Chest pain\n**Symptoms:** \n- Chest tightness\n- Coughing\n**Duration:** 2 days\n\n**Baseline Biometrics:**\n- Heart Rate: 92 bpm\n- Oxygen Level: 98%\n\nNote: There is no 'latest_biometrics' data from a smartwatch included in the provided information, so a 'Wearable Data' section is not applicable at this time."
                    }
                """.trimIndent()
            }
            uri.endsWith("/api/logs/all") -> {
                // Fake timeline data
                responseString = """
                    [
                        {
                            "id": 101,
                            "date": "April 4, 2026 - 14:30",
                            "clinicalReport": "Patient reported sharp lower abdominal pain."
                        }
                    ]
                """.trimIndent()
            }
            uri.endsWith("/api/share") -> {
                responseString = """
                    {
                        "shareUrl": "https://credence-med.com/doctor/view/abc-123",
                        "pin": "4092",
                        "expiresAt": "60 Minutes"
                    }
                """.trimIndent()
            }
            else -> {
                responseString = "{ \"error\": \"Route not mocked\" }"
            }
        }


        Thread.sleep(1500)

        return Response.Builder()
            .code(200)
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(responseString.toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json")
            .build()
    }
}


object NetworkClient {

    const val BASE_URL = "http://10.0.2.2:8080"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(MockInterceptor())
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}