using StomatologyAPI.Abstract;
using StomatologyAPI.Models;
using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Infrastructure
{
    /// <summary>
    /// Паттерн UnitOfWork
    /// Используется для того, чтобы все репозитории
    /// имели один контекст БД
    /// </summary>
    public class UnitOfWork : IUnitOfWork
    {
        private readonly ApplicationDbContext context;
        private Dictionary<Type, object> repositories;

        public UnitOfWork()
        {
            context = new ApplicationDbContext();
        }

        public void Dispose()
        {
            if (context != null) context.Dispose();
        }

        /// <summary>
        /// Возвращает репозиторий для заданного типа модели
        /// </summary>
        /// <typeparam name="T">Тип модели</typeparam>
        /// <returns></returns>
        public IRepository<T> GetRepository<T>() where T : class, IModel
        {
            if (repositories == null)
                repositories = new Dictionary<Type, object>();

            var type = typeof(T);

            if (!repositories.ContainsKey(type))
                repositories.Add(type, new Repository<T>(context));

            return (IRepository<T>)repositories[type];
        }

    }
}