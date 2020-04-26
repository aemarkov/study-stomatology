using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace StomatologyAPI.Abstract
{
    /// <summary>
	/// Интерфейс для Unit Of Work - 
	/// паттерна для того, чтобы все репозитории
	/// использовали один и тот же контекст
	/// </summary>
	public interface IUnitOfWork : IDisposable
    {
        /// <summary>
        /// Возвоащает репозиторий для заданной модели
        /// </summary>
        /// <typeparam name="T">Класс модели</typeparam>
        /// <returns></returns>
        IRepository<T> GetRepository<T>() where T : class, IModel;
    }
}
