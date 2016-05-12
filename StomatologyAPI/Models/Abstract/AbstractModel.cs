using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models.Abstract
{
    /// <summary>
    /// Базовый класс модели
    /// </summary>
    public abstract class AbstractModel : IModel
    {
        /// <summary>
        /// Первичный ключ
        /// </summary>
        public int Id { get; set; }
    }

}