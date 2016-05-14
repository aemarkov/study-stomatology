using StomatologyAPI.Controllers.Abstract;
using StomatologyAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using StomatologyAPI.Abstract;
using System.Data.Entity;

namespace StomatologyAPI.Controllers
{
    public class SubcategoryController : AbstractAPIController<Subcategory>
    {
        public SubcategoryController(IUnitOfWork uof) : base(uof)
        {
        }

        public override Subcategory Get(int id)
        {
            return m_repository.Entities.Include(x => x.Procedures).FirstOrDefault(x => x.Id == id);
        }

        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Put([FromBody] Subcategory value)
        {
            return base.Put(value);
        }

        [Authorize(Roles = "admin")]
        public override HttpResponseMessage Post([FromBody] Subcategory value)
        {
            return base.Post(value);
        }

        [Authorize(Roles ="admin")]
        public override HttpResponseMessage Delete(int id)
        {
            return base.Delete(id);
        }

    }
}
