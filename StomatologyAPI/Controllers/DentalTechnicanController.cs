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
using StomatologyAPI.Models.BindingModels;

namespace StomatologyAPI.Controllers
{
    [RoutePrefix("api/DentalTechnican")]
    public class DentalTechnicanController : AbstractAPIController<DentalTechnican>
    {
        public DentalTechnicanController(IUnitOfWork uof) : base(uof)
        {

        }

        [Authorize(Roles = "admin,doctor")]
        public override IEnumerable<DentalTechnican> Get()
        {
            return base.Get();
        }

        [Authorize(Roles = "admin,doctor")]
        public override DentalTechnican Get(int id)
        {
            return base.Get(id);
        }

        //Создание нового запрещено
        public override HttpResponseMessage Put([FromBody] DentalTechnican value)
        {
            return ResponseCreator.GenerateResponse(HttpStatusCode.BadRequest, "To register DentalTechnican use \"api/Account/RegisterDentalTechnican\"");
        }

        // Редактирование доктора
        [Authorize(Roles = "admin")]
        public HttpResponseMessage Post(BaseBindingModel value)
        {
            try
            {
                DentalTechnican technican = m_repository.GetById(value.Id);
                technican.PersonInfo.Name = value.Name;
                technican.PersonInfo.Surname = value.Surname;
                technican.PersonInfo.Middlename = value.Middlename;
                m_repository.Update(technican);

                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (EntityAlreadyExistsException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.BadRequest, exp);
            }
            catch (EntityNotFoundException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, exp);
            }
            catch (Exception exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.InternalServerError, exp);
            }
        }



        //Это говнокостыль. Эти методы определны в базовом, но тут нам не нужны
        [Route("undefined_method_1")]
        public override HttpResponseMessage Post([FromBody] DentalTechnican value)
        {
            return new HttpResponseMessage(HttpStatusCode.BadGateway);
        }

        [Authorize(Roles ="admin")]
        public override HttpResponseMessage Delete(int id)
        {
            return base.Delete(id);
        }
    }
}
