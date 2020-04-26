using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace StomatologyAPI.Models.Abstract
{
    /// <summary>
    /// Интерфейс модели
    /// Служит, чтобы связать в иеархии модели и ApplicationUser
    /// </summary>
    public interface IModel
    {
        int Id { get; set; }
    }
}
