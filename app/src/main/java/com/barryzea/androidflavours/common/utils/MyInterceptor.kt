package com.barryzea.androidflavours.common.utils

import android.content.Context
import com.barryzea.androidflavours.R
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

class MyInterceptor @Inject constructor(private val context:Context):Interceptor {
    private var tryCount = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request:Request = chain.request()
        val response = chain.proceed(request)
        return when(response.code){
            400->{setResponse(response,chain,400, context.getString(R.string.msg_code_400))}
            401->{setResponse(response,chain,401, context.getString(R.string.msg_code_401))}
            403->{setResponse(response,chain,403, context.getString(R.string.msg_code_403))}
            404->{setResponse(response,chain,404, context.getString(R.string.msg_code_404))}
            405->{setResponse(response,chain,405, context.getString(R.string.msg_code_405))}
            406->{setResponse(response,chain,406, context.getString(R.string.msg_code_406))}
            429->{setResponse(response,chain,429, context.getString(R.string.msg_code_429))}
            503->{setResponse(response,chain,503, context.getString(R.string.msg_code_503))}
            200->{setResponse(response,chain,200, context.getString(R.string.msg_code_200))}
            201->{setResponse(response,chain,201, context.getString(R.string.msg_code_201))}
            else->{
                //Volvemos a llamar a la api  cinco veces más si hay algún error desconocido
                if(!response.isSuccessful && tryCount<5){
                    Thread.sleep(2000)
                    tryCount++
                    response.close()
                    chain.call().clone().execute()

                }
                //Después de cumplir las cinco llamadas y no obtener un respuesta satisfactoria
                //posteamos el mensaje recibido
                setResponse(response,chain,429,response.message)

            }
        }
    }

    private fun setResponse(response: Response,chain: Interceptor.Chain,code:Int, msg:String):Response{
        return response.newBuilder()
            .code(code)
            .body(response.body)
            .protocol(Protocol.HTTP_2)
            .message(msg)
            .request(chain.request())
            .build()
    }
}