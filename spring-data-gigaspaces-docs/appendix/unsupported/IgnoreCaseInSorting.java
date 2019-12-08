// Order.ignoreCase() is not supported
Sort sorting = new Sort(new Order(ASC, "id").ignoreCase());
// will throw an UnsupportedOperationException
personRepository.findByNameEquals("paul", new PageRequest(1, 2, sorting));