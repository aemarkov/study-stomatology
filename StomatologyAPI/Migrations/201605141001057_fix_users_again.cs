namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class fix_users_again : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Patients", "ApplicationUserId", "dbo.AspNetUsers");
            DropIndex("dbo.Patients", new[] { "ApplicationUserId" });
            AlterColumn("dbo.Patients", "ApplicationUserId", c => c.Int());
            CreateIndex("dbo.Patients", "ApplicationUserId");
            AddForeignKey("dbo.Patients", "ApplicationUserId", "dbo.AspNetUsers", "Id");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Patients", "ApplicationUserId", "dbo.AspNetUsers");
            DropIndex("dbo.Patients", new[] { "ApplicationUserId" });
            AlterColumn("dbo.Patients", "ApplicationUserId", c => c.Int(nullable: false));
            CreateIndex("dbo.Patients", "ApplicationUserId");
            AddForeignKey("dbo.Patients", "ApplicationUserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
        }
    }
}
