namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Init : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Categories",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Image = c.String(maxLength: 255),
                        Name = c.String(maxLength: 64),
                        Description = c.String(),
                    })
                .PrimaryKey(t => t.ID);
            
            CreateTable(
                "dbo.Subcategories",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        CategoryID = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.ID)
                .ForeignKey("dbo.Categories", t => t.CategoryID, cascadeDelete: true)
                .Index(t => t.CategoryID);
            
            CreateTable(
                "dbo.Procedures",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Description = c.String(),
                        SubcategoryID = c.Int(nullable: false),
                        Cost = c.Int(nullable: false),
                        PatientVisit_ID = c.Int(),
                    })
                .PrimaryKey(t => t.ID)
                .ForeignKey("dbo.Subcategories", t => t.SubcategoryID, cascadeDelete: true)
                .ForeignKey("dbo.PatientVisits", t => t.PatientVisit_ID)
                .Index(t => t.SubcategoryID)
                .Index(t => t.PatientVisit_ID);
            
            CreateTable(
                "dbo.ClinicInfoes",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false),
                        PhoneNumber = c.String(),
                        Email = c.String(),
                        Adress = c.String(),
                    })
                .PrimaryKey(t => t.ID);
            
            CreateTable(
                "dbo.Orders",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Date = c.DateTime(),
                        IsFinished = c.Boolean(nullable: false),
                        Annotation = c.String(),
                        PatientID = c.Int(nullable: false),
                        DentalTechnicanID = c.Int(nullable: false),
                        ClinicInfoID = c.Int(nullable: false),
                        Technican_Id = c.String(maxLength: 128),
                        Patient_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.ID)
                .ForeignKey("dbo.ClinicInfoes", t => t.ClinicInfoID, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.Technican_Id)
                .ForeignKey("dbo.AspNetUsers", t => t.Patient_Id)
                .Index(t => t.ClinicInfoID)
                .Index(t => t.Technican_Id)
                .Index(t => t.Patient_Id);
            
            CreateTable(
                "dbo.AspNetUsers",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Surname = c.String(nullable: false, maxLength: 64),
                        Name = c.String(nullable: false, maxLength: 64),
                        Middlename = c.String(maxLength: 64),
                        Email = c.String(maxLength: 256),
                        EmailConfirmed = c.Boolean(nullable: false),
                        PasswordHash = c.String(),
                        SecurityStamp = c.String(),
                        PhoneNumber = c.String(),
                        PhoneNumberConfirmed = c.Boolean(nullable: false),
                        TwoFactorEnabled = c.Boolean(nullable: false),
                        LockoutEndDateUtc = c.DateTime(),
                        LockoutEnabled = c.Boolean(nullable: false),
                        AccessFailedCount = c.Int(nullable: false),
                        UserName = c.String(nullable: false, maxLength: 256),
                        Image = c.String(maxLength: 255),
                        Text = c.String(),
                        MedicalCardNumber = c.Int(),
                        Age = c.Int(),
                        IsMen = c.Boolean(),
                        Discriminator = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.UserName, unique: true, name: "UserNameIndex");
            
            CreateTable(
                "dbo.AspNetUserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.String(nullable: false, maxLength: 128),
                        ClaimType = c.String(),
                        ClaimValue = c.String(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserLogins",
                c => new
                    {
                        LoginProvider = c.String(nullable: false, maxLength: 128),
                        ProviderKey = c.String(nullable: false, maxLength: 128),
                        UserId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.LoginProvider, t.ProviderKey, t.UserId })
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserRoles",
                c => new
                    {
                        UserId = c.String(nullable: false, maxLength: 128),
                        RoleId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.UserId, t.RoleId })
                .ForeignKey("dbo.AspNetRoles", t => t.RoleId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);
            
            CreateTable(
                "dbo.ToothWorks",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        ToothNo = c.Int(nullable: false),
                        ProcedureID = c.Int(nullable: false),
                        OrderID = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.ID)
                .ForeignKey("dbo.TechnicanProcedures", t => t.ProcedureID, cascadeDelete: true)
                .ForeignKey("dbo.Orders", t => t.OrderID, cascadeDelete: true)
                .Index(t => t.ProcedureID)
                .Index(t => t.OrderID);
            
            CreateTable(
                "dbo.TechnicanProcedures",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Cost = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.ID);
            
            CreateTable(
                "dbo.PatientVisits",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Date = c.DateTime(),
                        Annotation = c.String(),
                        PatientID = c.Int(nullable: false),
                        DoctorID = c.Int(nullable: false),
                        Doctor_Id = c.String(maxLength: 128),
                        Patient_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.ID)
                .ForeignKey("dbo.AspNetUsers", t => t.Doctor_Id)
                .ForeignKey("dbo.AspNetUsers", t => t.Patient_Id)
                .Index(t => t.Doctor_Id)
                .Index(t => t.Patient_Id);
            
            CreateTable(
                "dbo.AspNetRoles",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.Name, unique: true, name: "RoleNameIndex");
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.PatientVisits", "Patient_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.Orders", "Patient_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropForeignKey("dbo.Procedures", "PatientVisit_ID", "dbo.PatientVisits");
            DropForeignKey("dbo.PatientVisits", "Doctor_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.ToothWorks", "OrderID", "dbo.Orders");
            DropForeignKey("dbo.ToothWorks", "ProcedureID", "dbo.TechnicanProcedures");
            DropForeignKey("dbo.Orders", "Technican_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.Orders", "ClinicInfoID", "dbo.ClinicInfoes");
            DropForeignKey("dbo.Subcategories", "CategoryID", "dbo.Categories");
            DropForeignKey("dbo.Procedures", "SubcategoryID", "dbo.Subcategories");
            DropIndex("dbo.AspNetRoles", "RoleNameIndex");
            DropIndex("dbo.PatientVisits", new[] { "Patient_Id" });
            DropIndex("dbo.PatientVisits", new[] { "Doctor_Id" });
            DropIndex("dbo.ToothWorks", new[] { "OrderID" });
            DropIndex("dbo.ToothWorks", new[] { "ProcedureID" });
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.AspNetUsers", "UserNameIndex");
            DropIndex("dbo.Orders", new[] { "Patient_Id" });
            DropIndex("dbo.Orders", new[] { "Technican_Id" });
            DropIndex("dbo.Orders", new[] { "ClinicInfoID" });
            DropIndex("dbo.Procedures", new[] { "PatientVisit_ID" });
            DropIndex("dbo.Procedures", new[] { "SubcategoryID" });
            DropIndex("dbo.Subcategories", new[] { "CategoryID" });
            DropTable("dbo.AspNetRoles");
            DropTable("dbo.PatientVisits");
            DropTable("dbo.TechnicanProcedures");
            DropTable("dbo.ToothWorks");
            DropTable("dbo.AspNetUserRoles");
            DropTable("dbo.AspNetUserLogins");
            DropTable("dbo.AspNetUserClaims");
            DropTable("dbo.AspNetUsers");
            DropTable("dbo.Orders");
            DropTable("dbo.ClinicInfoes");
            DropTable("dbo.Procedures");
            DropTable("dbo.Subcategories");
            DropTable("dbo.Categories");
        }
    }
}
