variable "hashicorp_vault_role_id" {
  description = "The role identifier used when retrieving configuration from Hashicorp Vault"
  type = string
}

variable "hashicorp_vault_secret_id" {
  description = "The secret identifier used when retrieving configuration from Hashicorp Vault"
  type = string
}

provider "vault" {
  auth_login {
    path = "auth/approle/login"

    parameters = {
      role_id   = var.hashicorp_vault_role_id
      secret_id = var.hashicorp_vault_secret_id
    }
  }
}
