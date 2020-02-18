using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MasterWorkerModel
{
    public class ServiceData
    {
        public String ID { get; set; }
        public bool IsError { get; set; }
        public Result[] Data { get; set; }
    }
}
