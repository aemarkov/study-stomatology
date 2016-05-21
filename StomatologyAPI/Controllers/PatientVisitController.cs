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
using Microsoft.AspNet.Identity;

namespace StomatologyAPI.Controllers
{
    /// <summary>
    /// Контроллер для управления посещениями врача
    /// </summary>
    [RoutePrefix("api/PatientVisit")]
    public class PatientVisitController : AbstractAPIController<PatientVisit>
    {
        private IRepository<Procedure> procedure_repository;
        private IRepository<Doctor> doctor_repository;

        public PatientVisitController(IUnitOfWork uof) : base(uof)
        {
            procedure_repository = uof.GetRepository<Procedure>();
            doctor_repository = uof.GetRepository<Doctor>();
        }


        //Возвращает доктора текущего пользователя
        Doctor get_current_doctor()
        {
            var user_id = System.Web.HttpContext.Current.User.Identity.GetUserId<int>();
            return doctor_repository.Entities.FirstOrDefault(x => x.ApplicationUserId == user_id);
        }

        [Authorize(Roles = "admin, doctor")]
        public override IEnumerable<PatientVisit> Get()
        {
            return base.Get();
        }

        [Authorize(Roles = "admin, doctor")]
        public override PatientVisit Get(int id)
        {
            return m_repository.Entities.Include(x => x.Procedures).FirstOrDefault(x => x.Id == id);
        }

        [Authorize(Roles = "doctor")]
        public override HttpResponseMessage Put([FromBody] PatientVisit value)
        {
            var doctor = get_current_doctor();
            if (doctor == null) throw new EntityNotFoundException();

            value.Date = DateTime.Now;
            value.Doctor = doctor;

            return base.Put(value);
        }

        [Authorize(Roles ="doctor")]
        public override HttpResponseMessage Post([FromBody] PatientVisit value)
        {
			try
			{
				if (value.IsClosed) throw new EntityIsClosedException();

				var doctor = get_current_doctor();
				if (doctor == null) throw new EntityNotFoundException();

				value.Doctor = doctor;

				return base.Post(value);
			}
			catch (EntityIsClosedException exp)
			{
				return ResponseCreator.GenerateResponse(HttpStatusCode.Forbidden, exp);
			}
		}

        [Authorize(Roles = "doctor")]
        //Добавляет процедуру в посещение
        [Route("AddProcedure")]
		[HttpPost]
        public HttpResponseMessage AddProcedure(int visitId, int procedureId)
        {
            try
            {
                var visit = m_repository.Entities.Include(x => x.Procedures).FirstOrDefault(x => x.Id == visitId);
                if (visit == null) throw new EntityNotFoundException();
				if (visit.IsClosed)throw new EntityIsClosedException();

				visit.Procedures.Add(procedure_repository.GetById(procedureId));
                m_repository.Update(visit);

                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch(EntityNotFoundException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, exp);
            }
			catch (EntityIsClosedException exp)
			{
				return ResponseCreator.GenerateResponse(HttpStatusCode.Forbidden, exp);
			}
		}

        [Authorize(Roles = "doctor")]
        [Route("RemoveProcedure")]
		[HttpDelete]
        //Удаляет процедуру из посещения
        public HttpResponseMessage RemoveProcedure(int visitId, int procedureId)
        {
            try
            {
                var visit = m_repository.Entities.Include(x => x.Procedures).FirstOrDefault(x => x.Id == visitId);
                if (visit == null) throw new EntityNotFoundException();
				if (visit.IsClosed)throw new EntityIsClosedException();

				var procedure = visit.Procedures.FirstOrDefault(x => x.Id == procedureId);
				if (procedure == null) throw new EntityNotFoundException();

				visit.Procedures.Remove(procedure);
                m_repository.Update(visit);

                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (EntityNotFoundException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, exp);
            }
			catch (EntityIsClosedException exp)
			{
				return ResponseCreator.GenerateResponse(HttpStatusCode.Forbidden, exp);
			}
		}


		[Authorize(Roles ="doctor")]
		[Route("Close/{visitId}")]
		[HttpGet]
		public HttpResponseMessage Close(int visitId)
		{
			try
			{
				var visit = m_repository.GetById(visitId);
				if (visit == null)
					throw new EntityNotFoundException();

				visit.IsClosed = true;
				m_repository.Update(visit);

				return new HttpResponseMessage(HttpStatusCode.OK);
			}
			catch (EntityNotFoundException exp)
			{
				return ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, exp);
			}
		}
	}
}
