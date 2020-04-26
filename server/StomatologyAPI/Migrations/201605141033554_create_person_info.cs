namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class create_person_info : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.PersonInfo",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Surname = c.String(nullable: false, maxLength: 64),
                        Name = c.String(nullable: false, maxLength: 64),
                        Middlename = c.String(maxLength: 64),
                    })
                .PrimaryKey(t => t.Id);
            
            AddColumn("dbo.DentalTechnicans", "PersonInfoId", c => c.Int(nullable: false));
            AddColumn("dbo.Patients", "PersonInfoId", c => c.Int(nullable: false));
            AddColumn("dbo.Doctors", "PersonInfoId", c => c.Int(nullable: false));
            CreateIndex("dbo.DentalTechnicans", "PersonInfoId");
            CreateIndex("dbo.Patients", "PersonInfoId");
            CreateIndex("dbo.Doctors", "PersonInfoId");
            AddForeignKey("dbo.Patients", "PersonInfoId", "dbo.PersonInfo", "Id", cascadeDelete: true);
            AddForeignKey("dbo.Doctors", "PersonInfoId", "dbo.PersonInfo", "Id", cascadeDelete: true);
            AddForeignKey("dbo.DentalTechnicans", "PersonInfoId", "dbo.PersonInfo", "Id", cascadeDelete: true);
            DropColumn("dbo.AspNetUsers", "Surname");
            DropColumn("dbo.AspNetUsers", "Name");
            DropColumn("dbo.AspNetUsers", "Middlename");
        }
        
        public override void Down()
        {
            AddColumn("dbo.AspNetUsers", "Middlename", c => c.String(maxLength: 64));
            AddColumn("dbo.AspNetUsers", "Name", c => c.String(nullable: false, maxLength: 64));
            AddColumn("dbo.AspNetUsers", "Surname", c => c.String(nullable: false, maxLength: 64));
            DropForeignKey("dbo.DentalTechnicans", "PersonInfoId", "dbo.PersonInfo");
            DropForeignKey("dbo.Doctors", "PersonInfoId", "dbo.PersonInfo");
            DropForeignKey("dbo.Patients", "PersonInfoId", "dbo.PersonInfo");
            DropIndex("dbo.Doctors", new[] { "PersonInfoId" });
            DropIndex("dbo.Patients", new[] { "PersonInfoId" });
            DropIndex("dbo.DentalTechnicans", new[] { "PersonInfoId" });
            DropColumn("dbo.Doctors", "PersonInfoId");
            DropColumn("dbo.Patients", "PersonInfoId");
            DropColumn("dbo.DentalTechnicans", "PersonInfoId");
            DropTable("dbo.PersonInfo");
        }
    }
}
