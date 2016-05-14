using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Infrastructure
{
    public class DatabaseException : Exception
    {
        public DatabaseException() : base("Exception in database") { }
        public DatabaseException(String message) : base(message) { }
    }

    public class EntityAlreadyExistsException:DatabaseException
    {
        public EntityAlreadyExistsException() : base("Entity already exists") { }
    }

    public class EntityNotFoundException:DatabaseException
    {
        public EntityNotFoundException():base("Entityt not found") { }
    }
}