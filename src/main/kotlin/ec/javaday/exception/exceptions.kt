package ec.javaday.exception

// TODO: Explain exception are uncatch
class ResourceNotFoundException(message: String): Exception(message)
class BusinessRuleException(message: String): Exception(message)
