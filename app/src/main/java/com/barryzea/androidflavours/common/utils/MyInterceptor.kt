package com.barryzea.androidflavours.common.utils

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

class MyInterceptor:Interceptor {
    private var tryCount = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request:Request = chain.request()
        val response = chain.proceed(request)
        return when(response.code){
            400->{setResponse(response,chain,400,response.message)}
            401->{setResponse(response,chain,401,response.message)}
            403->{setResponse(response,chain,403,response.message)}
            404->{setResponse(response,chain,404,response.message)}
            200->{setResponse(response,chain,200,"Respuesta de la API completada correctamente")}
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