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
    /// <summary>
    /// Контроллер категории
    /// - просмотр всех
	/// - просмотр одной(+ подкатегории + процедуры)
	/// - Редактирование А
	/// - УДаление А
    /// </summary>
    public class CategoryController : AbstractAPIController<Category>
    {
        public CategoryController(IUnitOfWork uof) : base(uof)
        {
        }

        [Authorize(Roles = "Admin")]
        public override HttpResponseMessage Put([FromBody] Category value)
        {
            return base.Put(value);
        }

        [Authorize(Roles ="Admin")]
        public override HttpResponseMessage Delete(int id)
        {
            return base.Delete(id);
        }
    }
}
