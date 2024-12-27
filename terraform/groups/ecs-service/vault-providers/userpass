variable "hashicorp_vault_username" {
  description = "The username used when retrieving configuration from Hashicorp Vault"
  type = string
}

variable "hashicorp_vault_password" {
  description = "The password used when retrieving configuration from Hashicorp Vault"
  type = string
}

provider "vault" {
  auth_login {
    path = "auth/userpass/login/${var.hashicorp_vault_username}"

    parameters = {
      password = var.hashicorp_vault_password
    }
  }
}
