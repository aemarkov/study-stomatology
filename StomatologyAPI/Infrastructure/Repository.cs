using StomatologyAPI.Abstract;
using StomatologyAPI.Models;
using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Infrastructure
{
    /// <summary>
    /// Репозиторий, осуществляет доступ к бд
    /// </summary>
    /// <typeparam name="M">Класс модели</typeparam>
    public class Repository<M> : IRepository<M> where M: class, IModel
    {
        private ApplicationDbContext context;

        public Repository()
        {
            context = new ApplicationDbContext();
        }

        public Repository(ApplicationDbContext context)
        {
            this.context = context;
        }

        /// <summary>
        /// Возвращает список сущ
        /// </summary>
        public IQueryable<M> Entities { get { return context.Set<M>(); } }

        /// <summary>
        /// Создает новую сущность, либо сохраняет, если сущность
        /// отсуствует
        /// </summary>
        /// <param name="entity"></param>
        public void CreateOrUpdate(M entity)
        {
            if (!context.Set<M>().Any(x => x.Id == entity.Id))
                //Создаем
                context.Entry(entity).State = EntityState.Added;
            
            else
                //Сохраняем
                context.Entry(entity).State = EntityState.Modified;

            context.SaveChanges();
        }

        /// <summary>
        /// Удаляет сущность
        /// </summary>
        /// <param name="id">ID сущности</param>
        public void Delete(int id)
        {
            DbSet<M> set = context.Set<M>();
            M m = set.Find(id);
            if (m != null)
            {
                set.Remove(m);
                context.SaveChanges();
            }
            else
                throw new ArgumentNullException("Entity doesn't exist in repository");
        }

        /// <summary>
        /// Удаляет сущность
        /// </summary>
        /// <param name="entity">Сущность</param>
        public void Delete(M entity)
        {
            Delete(entity.Id);
        }

        /// <summary>
        /// Возвращает сущность по ID
        /// </summary>
        /// <param name="id"></param>
        public M GetById(int id)
        {
            return context.Set<M>().First(x => x.Id==id);
        }
    }
}