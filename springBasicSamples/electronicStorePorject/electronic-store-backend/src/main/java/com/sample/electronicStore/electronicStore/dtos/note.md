// why user DTO ?
// to avoid cyclic dependency, entities represent persistent data and are used for DB persistence and fetch.
// But for data transfer between controller and service layer we use DTO (Data Transfer Object).