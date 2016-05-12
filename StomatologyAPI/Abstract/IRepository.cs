using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace StomatologyAPI.Abstract
{
    /// <summary>
    /// Интерфейс репозитория
    /// </summary>
    public interface IRepository<M> where M : class, IModel
    {
        IQueryable<M> Entities { get;}
        M GetById(int id);
        void CreateOrUpdate(M entity);
        void Delete(M entity);
        void Delete(int id);
    }
}
