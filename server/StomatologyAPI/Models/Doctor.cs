using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    public class Doctor:AbstractModel
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


        /// <summary>
        /// Связь с пользователями
        /// </summary>
        [Required]
        public int ApplicationUserId { get; set; }
        public virtual ApplicationUser ApplicationUser { get; set; }

        /// <summary>
        /// Информация о человеке
        /// </summary>
        public int PersonInfoId { get; set; }
        public virtual PersonInfo PersonInfo { get; set; }

    }
}