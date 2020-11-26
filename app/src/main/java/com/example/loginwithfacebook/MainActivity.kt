package com.example.loginwithfacebook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var callbackManager: CallbackManager
    private var EMAIL = "email"
    private var FRIEND_L = "read_custom_friendlists"
    private var userNAme = "username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button.setOnClickListener(View.OnClickListener {
            //  LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("read_custom_friendlists", "email"));
            // login_button.setReadPermissions(listOf(EMAIL))
            login_button.setReadPermissions(Arrays.asList(EMAIL))
            callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    //Login with FB successful
                    //to get user data we will use Graph Api
                    val graphRequest = GraphRequest.newMeRequest(result?.accessToken) { obj, response ->

                        try {
                            if (obj.has("id")) {
                                Log.d("FACEBOOK_DATA", obj.getString("id"))
                                Log.d("FACEBOOK_DATA", obj.getString("name"))
                                Log.d("FACEBOOK_DATA", obj.getString("email"))

                                Log.d("FACEBOOK_DATA", JSONObject(obj.getString("picture")).getJSONObject("data").getString("url"))

                                Log.d("FACEBOOK_DATA", obj.getString("birthday"))
                                Log.d("FACEBOOK_DATA", JSONObject(obj.getString("friends")).getJSONObject("summary").getString("total_count"))
                                

                                tvUserName.text = obj.getString("name")
                                tvEmail.text = obj.getString("email")
                                tvBirthday.text = obj.getString("birthday")
                                tvFriendsCount.text = JSONObject(obj.getString("friends")).getJSONObject("summary").getString("total_count")
                                Glide.with(applicationContext).load(JSONObject(obj.getString("picture")).getJSONObject("data").getString("url")).into(imageView);


                            }

                        } catch (e: Exception) {
                        }

                    }

                    val param = Bundle()
                    param.putString("fields", "name,email,id,picture.type(large),birthday,friends")
                    graphRequest.parameters = param
                    graphRequest.executeAsync()


                    /* *//* make the API call *//*

                    *//* make the API call *//*GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/{friend-list-id}",
                            null,
                            HttpMethod.GET
                    ) {
                        *//* handle the result *//*

                    }.executeAsync()*/
                }

                override fun onCancel() {
                  //  TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException?) {
                 //   TODO("Not yet implemented")
                }

            })

        })

        /* make the API call *//*

        *//* make the API call *//*GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "id",
                null,
                HttpMethod.GET
        ) {
            Log.d("FACEBOOK_DATA", "id")
        }.executeAsync()

*/
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


}