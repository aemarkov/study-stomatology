using StomatologyAPI.Abstract;
using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace StomatologyAPI.Controllers.Abstract
{
    /// <summary>
	/// Базовый абстрактный контроллер, которой реализует все базовые REST операции
	/// </summary>
	/// <typeparam name="M">Тип модели</typeparam>
	public abstract class AbstractAPIController<M> : ApiController where M : class, IModel
    {
        protected IRepository<M> m_repository;

        public AbstractAPIController(IUnitOfWork uof)
        {
            m_repository = uof.GetRepository<M>();
        }

        //Вернуть все
        public virtual IEnumerable<M> Get() =>  m_repository.Entities.ToList(); 
        

        //Вернуть один
        virtual public M Get(int id) => m_repository.GetById(id);


        //Изменить
        virtual public HttpResponseMessage Post([FromBody]M value)
        {
            try
            {
                m_repository.CreateOrUpdate(value);
                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (Exception exp)
            {
                return new HttpResponseMessage(HttpStatusCode.InternalServerError);
            }
        }

        //Создать
        virtual public HttpResponseMessage Put([FromBody]M value)
        {
            try
            {
                m_repository.CreateOrUpdate(value);
                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (Exception exp)
            {
                return new HttpResponseMessage(HttpStatusCode.InternalServerError);
            }
        }


        //Удалить
        virtual public HttpResponseMessage Delete(int id)
        {
            try
            {
                m_repository.Delete(id);
                return new HttpResponseMessage(HttpStatusCode.OK);
            }
            catch (ArgumentException exp)
            {
                return new HttpResponseMessage(HttpStatusCode.NotFound);
            }
            catch (Exception exp)
            {
                return new HttpResponseMessage(HttpStatusCode.InternalServerError);
            }
        }
    }
}