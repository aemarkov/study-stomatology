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
    public class TechnicanProcedureController : AbstractAPIController<TechnicanProcedure>
    {
        public TechnicanProcedureController(IUnitOfWork uof) : base(uof)
        {
        }

        [Authorize(Roles ="admin,doctor,dental_technican")]
        public override IEnumerable<TechnicanProcedure> Get()
        {
            return base.Get();
        }

        [Authorize(Roles = "admin,doctor,dental_technican")]
        public override TechnicanProcedure Get(int id)
        {
            return base.Get(id);
        }

        [Authorize(Roles = "admin,dental_technican")]
        public override HttpResponseMessage Put([FromBody] TechnicanProcedure value)
        {
            return base.Put(value);
        }

        [Authorize(Roles = "admin,dental_technican")]
        public override HttpResponseMessage Post([FromBody] TechnicanProcedure value)
        {
            return base.Post(value);
        }

        [Authorize(Roles = "admin,dental_technican")]
        public override HttpResponseMessage Delete(int id)
        {
            return base.Delete(id);
        }
    }
}
