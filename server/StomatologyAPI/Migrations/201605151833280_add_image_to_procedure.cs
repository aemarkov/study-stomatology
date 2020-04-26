namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class add_image_to_procedure : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Procedures", "Image", c => c.String(maxLength: 255));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Procedures", "Image");
        }
    }
}
