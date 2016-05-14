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

    }
}
