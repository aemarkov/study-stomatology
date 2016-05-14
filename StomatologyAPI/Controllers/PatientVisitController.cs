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
    /// <summary>
    /// Контроллер для управления посещениями врача
    /// </summary>
    [RoutePrefix("api/PatientVisit")]
    public class PatientVisitController : AbstractAPIController<PatientVisit>
    {
        private IRepository<Procedure> procedure_repository;

        public PatientVisitController(IUnitOfWork uof) : base(uof)
        {
            procedure_repository = uof.GetRepository<Procedure>();
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

        [Authorize(Roles = "admin, doctor")]
        public override HttpResponseMessage Put([FromBody] PatientVisit value)
        {
            return base.Put(value);
        }

        [Authorize(Roles ="admin, doctor")]
        public override HttpResponseMessage Post([FromBody] PatientVisit value)
        {
            return base.Post(value);
        }

        [Authorize(Roles = "admin, doctor")]
        //Добавляет процедуру в посещение
        [Route("AddProcedure")]
        public HttpResponseMessage AddProcedure(VisitProcedureBindingModel model)
        {
            try
            {
                var visit = m_repository.Entities.Include(x => x.Procedures).FirstOrDefault(x => x.Id == model.VisitId);
                if (visit == null) throw new EntityNotFoundException();

                visit.Procedures.Add(procedure_repository.GetById(model.ProcedureId));
                m_repository.Update(visit);

                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch(EntityNotFoundException exp)
            {
                return ResponseCreator.GenerateResponse(HttpStatusCode.NotFound, exp);
            }
        }

        [Authorize(Roles = "admin, doctor")]
        [Route("RemoveProcedure")]
        //Удаляет процедуру из посещения
        public HttpResponseMessage RemoveProcedure(VisitProcedureBindingModel model)
        {
            try
            {
                var visit = m_repository.Entities.Include(x => x.Procedures).FirstOrDefault(x => x.Id == model.VisitId);
                if (visit == null) throw new EntityNotFoundException();

                var procedure = visit.Procedures.ToList()[model.ProcedureId];
                visit.Procedures.Remove(procedure);
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
