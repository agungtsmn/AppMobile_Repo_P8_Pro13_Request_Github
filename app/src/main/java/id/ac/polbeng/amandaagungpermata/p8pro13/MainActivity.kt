package id.ac.polbeng.amandaagungpermata.p8pro13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            doAsync {
                val gitHubInfo = fetchGitHubInfo(inputUsername.text.toString())
                val jsonReader = JSONObject(gitHubInfo)
                activityUiThread {
                    val id = jsonReader.getString("id")
                    val name = jsonReader.getString("name")
                    val url = jsonReader.getString("url")
                    val blog = jsonReader.getString("blog")
                    val bio = jsonReader.getString("bio")
                    tvGitHub.text = "$id \n $name \n $url \n $blog \n $bio"
                }
            }
        }
    }

    private fun fetchGitHubInfo(login_id: String): String {
        val url = "https://api.github.com/users/$login_id"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val bodystr = response.body().string()
        return bodystr
    }
}