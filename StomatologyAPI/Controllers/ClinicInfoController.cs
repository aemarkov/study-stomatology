using StomatologyAPI.Controllers.Abstract;
using StomatologyAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using StomatologyAPI.Abstract;
using StomatologyAPI.Infrastructure;

namespace StomatologyAPI.Controllers
{
    /// <summary>
    /// Контроллер информации о клинике
    /// Информация о клинике может быть в 1м экземпляре
    /// </summary>
    [RoutePrefix("api/ClinincInfo")]
    public class ClinicInfoController : ApiController
    {
        IRepository<ClinicInfo> m_repository;

        public ClinicInfoController(IUnitOfWork uof)
        {
            m_repository = uof.GetRepository<ClinicInfo>();
        }

        public ClinicInfo Get()
        {
            return m_repository.Entities.FirstOrDefault();
        }

        public HttpResponseMessage Post([FromBody] ClinicInfo value)
        {
            try
            {
                m_repository.Update(value);
                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (EntityNotFoundException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, exp); //new HttpResponseMessage(HttpStatusCode.NotFound);
            }
            catch (Exception exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.InternalServerError, exp); //new HttpResponseMessage(HttpStatusCode.InternalServerError);
            }
        }

    }
}
