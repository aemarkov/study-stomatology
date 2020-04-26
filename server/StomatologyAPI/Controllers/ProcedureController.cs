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
    public class ProcedureController : AbstractAPIController<Procedure>
    {
        public ProcedureController(IUnitOfWork uof) : base(uof)
        {
        }


        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Put([FromBody] Procedure value)
        {
            return base.Put(value);
        }

        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Post([FromBody] Procedure value)
        {
            return base.Post(value);
        }

        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Delete(int id)
        {
            return base.Delete(id);
        }


    }
}
