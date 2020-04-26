using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web;

namespace StomatologyAPI.Infrastructure
{
    /// <summary>
    /// Этот класс служит для удобной генерации ответов с
    /// пояснениями в JSON
    /// </summary>
    public static class ResponseCreator
    {
        public static HttpResponseMessage GenerateResponse(HttpStatusCode code, string error_message)
        {
            var response = new HttpResponseMessage(code);
            dynamic body = new JObject();
            body.Message = error_message;
            var json = body.ToString();
            response.Content = new StringContent(json, Encoding.UTF8, "application/json");

            return response;
        }

        public static HttpResponseMessage GenerateResponse(HttpStatusCode code, Exception exp)
        {
            var response = new HttpResponseMessage(code);
            dynamic body = new JObject();
            body.Message = "Произошла ошибка";
            body.ExceptionMessage = exp.Message;
            body.ExceptionType = exp.GetType().ToString();
            body.StackTrace = exp.StackTrace;

            var json = body.ToString();
            response.Content = new StringContent(json, Encoding.UTF8, "application/json");

            return response;
        }
    }
}