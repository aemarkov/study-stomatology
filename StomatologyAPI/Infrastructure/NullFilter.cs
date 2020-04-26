using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.Filters;

namespace StomatologyAPI.Infrastructure
{
    public class NullFilter:ActionFilterAttribute
    {
        

        public override void OnActionExecuted(HttpActionExecutedContext actionExecutedContext)
        {
            var response = actionExecutedContext.Response;
			if (response == null) return;

            object responseValue;
            bool hasContent = response.TryGetContentValue(out responseValue);

            if (!hasContent && response.Content!=null && response.StatusCode==HttpStatusCode.OK)
            {
                actionExecutedContext.Response =  ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, new EntityNotFoundException());
            }
        }
    }
}