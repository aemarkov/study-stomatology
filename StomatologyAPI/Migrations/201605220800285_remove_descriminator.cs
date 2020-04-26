namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class remove_descriminator : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.Orders", "Discriminator");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Orders", "Discriminator", c => c.String(nullable: false, maxLength: 128));
        }
    }
}
