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

    }
}
