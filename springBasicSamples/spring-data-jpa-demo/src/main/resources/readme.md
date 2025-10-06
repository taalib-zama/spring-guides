This service contains basic JPA methods,
Also i have implementd custom error code mapping which maps the error code to custom error message.
For example if we try to insert duplicate record it will throw error code 23505 which is
mapped to "Duplicate record found" message.
this will get stored in DB table error_codes and same is thrown in response.


