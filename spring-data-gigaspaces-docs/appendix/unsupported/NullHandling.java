// NullHandling other than NATIVE is not supported
Sort sorting = new Sort(new Order(ASC, "id", NullHandling.NULLS_FIRST));
// will throw an UnsupportedOperationException
personRepository.findByNameEquals("paul", new PageRequest(1, 2, sorting));