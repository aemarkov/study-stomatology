using StomatologyAPI.Controllers.Abstract;
using StomatologyAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using StomatologyAPI.Abstract;
using StomatologyAPI.Models.BindingModels;
using StomatologyAPI.Infrastructure;
using System.Data.Entity;

namespace StomatologyAPI.Controllers
{
    [RoutePrefix("api/Patient")]
    public class PatientController : AbstractAPIController<Patient>
    {
        public PatientController(IUnitOfWork uof) : base(uof)
        {
        }

        [Authorize(Roles = "admin, doctor")]
        public override IEnumerable<Patient> Get()
        {
            return base.Get();
        }

        [Authorize(Roles = "admin, doctor")]
        public override Patient Get(int id)
        {
            return m_repository.Entities.Include(x => x.Visits).Include(x=>x.Orders).FirstOrDefault(x => x.Id == id);
        }

        [Authorize(Roles = "admin, doctor")]
        public HttpResponseMessage Put([FromBody] PatientBindingModel value)
        {
            try
            {
                Patient patient = new Patient() { MedicalCardNumber = value.MedicalCardNumber, IsMen = value.IsMen, DateOfBirth = value.DateOfBirth };
                PersonInfo pi = new PersonInfo(value.Surname, value.Name, value.Middlename);
                patient.PersonInfo = pi;
                m_repository.Create(patient);

                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (EntityAlreadyExistsException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.BadRequest, exp); //new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            catch (Exception exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.InternalServerError, exp); //new HttpResponseMessage(HttpStatusCode.InternalServerError);
            }
        }

        [Authorize(Roles = "admin")]
        public HttpResponseMessage Post([FromBody] PatientBindingModel value)
        {
            try
            {
                Patient patient = m_repository.GetById(value.Id);
                patient.IsMen = value.IsMen;
                patient.MedicalCardNumber = value.MedicalCardNumber;
                patient.PersonInfo.Name = value.Name;
                patient.PersonInfo.Surname = value.Surname;
                patient.PersonInfo.Middlename = value.Middlename;

                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (EntityAlreadyExistsException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.BadRequest, exp); //new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            catch(EntityNotFoundException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, exp); //new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            catch (Exception exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.InternalServerError, exp);//new HttpResponseMessage(HttpStatusCode.InternalServerError);
            }


        }

        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Delete(int id)
        {
            return base.Delete(id);
        }

        [Authorize(Roles ="admin,doctor")]
        [Route("GetVisits")]
        public ICollection<PatientVisit> GetVisits(int patientId)
        {
            return m_repository.Entities.Include(x => x.Visits).FirstOrDefault(x => x.Id == patientId)?.Visits;
        }




        //Это говнокостыль. Эти методы определны в базовом, но тут нам не нужны
        [Route("undefined_method_1")]
        public override HttpResponseMessage Put([FromBody] Patient value)
        {
            return new HttpResponseMessage(HttpStatusCode.BadGateway);
        }

        [Route("undefined_method_1")]
        public override HttpResponseMessage Post([FromBody] Patient value)
        {
            return new HttpResponseMessage(HttpStatusCode.BadGateway);
        }
    }
}
