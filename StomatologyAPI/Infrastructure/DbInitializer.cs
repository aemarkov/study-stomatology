using Microsoft.AspNet.Identity;
using StomatologyAPI.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Infrastructure
{
    /// <summary>
    /// Инициализатор БД
    /// Занес в БД роли и учетку Админа
    /// </summary>
    public class DbInitializer : CreateDatabaseIfNotExists<ApplicationDbContext>
    {
        protected override void Seed(ApplicationDbContext context)
        {
            var userManager = new ApplicationUserManager(new CustomUserStore(context));

            var roleManager = new RoleManager<CustomRole,int>(new CustomRoleStore(context));

            // создаем две роли
            var admin_role = new CustomRole { Name = "admin" };
            var patient_role = new CustomRole { Name = "patient" };
            var doctor_role = new CustomRole { Name = "doctor" };
            var dental_technican_role = new CustomRole { Name = "dental_technican" };

            // добавляем роли в бд
            roleManager.Create(admin_role);
            roleManager.Create(patient_role);
            roleManager.Create(doctor_role);
            roleManager.Create(dental_technican_role);

            // создаем пользователей
            var admin = new ApplicationUser { Email = "admin@somemail.ru", UserName = "admin" };
            string password = "adminadmin";
            var result = userManager.Create(admin, password);

            // если создание пользователя прошло успешно
            if (result.Succeeded)
            {
                // добавляем для пользователя роль
                userManager.AddToRole(admin.Id, admin_role.Name);
            }

            base.Seed(context);
        }
    }
}