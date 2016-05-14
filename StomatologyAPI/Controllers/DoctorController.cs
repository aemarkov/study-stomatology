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
    /// <summary>
    /// Контроллер врача (CURD)
    /// </summary>
    [RoutePrefix("api/Doctor")]
    public class DoctorController : AbstractAPIController<Doctor>
    {
        public DoctorController(IUnitOfWork uof) : base(uof)
        {
        }

        //Создание нового запрещено
        public override HttpResponseMessage Put([FromBody] Doctor value)
        {
            return ResponseCreator.GenerateResponse(HttpStatusCode.BadRequest, "To register Doctor use \"api/Account/RegisterDoctor\"");
        }

        // Редактирование доктора
        [Authorize(Roles ="admin")]
        public HttpResponseMessage Post(DoctorBindingModel value)
        {
            try
            {
                Doctor doctor = m_repository.GetById(value.Id);
                doctor.Image = value.Image;
                doctor.Text = value.Text;
                doctor.PersonInfo.Name = value.Name;
                doctor.PersonInfo.Surname = value.Surname;
                doctor.PersonInfo.Middlename = value.Middlename;

                m_repository.Update(doctor);

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
        public override HttpResponseMessage Post([FromBody] Doctor value)
        {
            return new HttpResponseMessage(HttpStatusCode.BadGateway);
        }
    }
}
