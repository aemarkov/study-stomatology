using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    public class Doctor:ApplicationUser
    {
        /// <summary>
        /// Описывает врача:
        /// - ФИО врача
        /// - Фотография врача
        /// - Статья с описанием
        /// - Связь с процедурами
        /// </summary>
        [MaxLength(255)]
        public string Image { get; set; }


        /// <summary>
        /// Описание врача
        /// </summary>
        public string Text { get; set; }


        /////////////////////////////////// КОНСТРУКТОРЫ //////////////////////////////////////////
        public Doctor() { }
        public Doctor(string surname, string name, string middlename = null) : base(surname, name, middlename)
        {
        }
    }
}