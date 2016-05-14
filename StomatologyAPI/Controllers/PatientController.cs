using StomatologyAPI.Controllers.Abstract;
using StomatologyAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using StomatologyAPI.Abstract;

namespace StomatologyAPI.Controllers
{
    [RoutePrefix("api/Patient")]
    public class PatientController : AbstractAPIController<Patient>
    {
        public PatientController(IUnitOfWork uof) : base(uof)
        {
        }

        [Authorize(Roles = "admin,doctor")]
        public override IEnumerable<Patient> Get()
        {
            return base.Get();
        }

        [Authorize(Roles = "admin,doctor")]
        public override Patient Get(int id)
        {
            return base.Get(id);
        }

        [Authorize(Roles = "admin,doctor")]
        public override HttpResponseMessage Put([FromBody] Patient value)
        {
            return base.Put(value);
        }

        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Post([FromBody] Patient value)
        {
            return base.Post(value);
        }

        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Delete(int id)
        {
            return base.Delete(id);
        }

        [Authorize(Roles ="admin,doctor")]
        [Route("GetVisits/{id}")]
        public ICollection<PatientVisit> GetVisits(int id)
        {
            return m_repository.GetById(id).Visits;
        }
    }
}
