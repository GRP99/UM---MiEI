import os

import pprint 
from pymongo import MongoClient
from pymongo.encryption import Algorithm,ClientEncryption
from pymongo.encryption_options import AutoEncryptionOpts


def main():
    # This must be the same master key that was used to create the encryption key.
    # os.urandom(n) -> str : Return n random bytes suitable for cryptographic use.
    local_master_key = os.urandom(96)

    # Key Management Service : Specifies that the key is from a local provider.
    kms_providers = {"local": {"key": local_master_key}}

    # The MongoDB namespace (db.collection) used to store the encryption data keys.
    key_vault_namespace = "encryption.pymongoTestKeyVault"
    # Split the namespace to get the database and collection name.
    key_vault_db_name, key_vault_coll_name = key_vault_namespace.split(".", 1)

    ## bypass_auto_encryption=True disable automatic encryption but keeps
    ## the automatic _decryption_ behavior. 

    auto_encryption_opts = AutoEncryptionOpts(
        kms_providers, key_vault_namespace, bypass_auto_encryption=True)

    # A single instance of MongoDB with auto_encryption_opts configuration .
    client = MongoClient(auto_encryption_opts=auto_encryption_opts)
    # students is an instance of a collection inside the test database . 
    students = client.SPLNEncrypted.students

    #Clear old data
    students.drop()

    # A key vault is a secure container for storing sensitive data.
    # Set up the key vault (key_vault_namespace) for this example.
    key_vault = client[key_vault_db_name][key_vault_coll_name]
    # Ensure that two data keys cannot share the same keyAltName.
    key_vault.drop()
    #key_vault.create_index(
    #    "keyAltNames",
    #    unique=True,
    #    partialFilterExpression={"keyAltNames": {"$exists": True}})


    """
    The ClientEncryption class encapsulates explicit operations on a key vault 
    collection that cannot be done directly on a MongoClient. 
    Similar to configuring auto encryption on a MongoClient, 
    it is constructed with a MongoClient (to a MongoDB cluster containing the key vault collection), 
    KMS provider configuration, and keyVaultNamespace. 
    It provides an API for explicitly encrypting and decrypting values, and creating data keys.
    """
    client_encryption = ClientEncryption(
        kms_providers,
        key_vault_namespace,
        # The MongoClient to use for reading/writing to the key vault.
        client,
        # The CodecOptions class used for encrypting and decrypting.
        students.codec_options)

    # Create a new data key for the encryptedField.
    data_key_id = client_encryption.create_data_key(
        'local', key_alt_names=['pymongo_encryption_example'])

    # Explicitly encrypt a field:
    password = client_encryption.encrypt(
        "123456789",
        Algorithm.AEAD_AES_256_CBC_HMAC_SHA_512_Deterministic,
        key_alt_name='pymongo_encryption_example')

    # User with encrypted password 
    user = {
		"username":"Etienne Costa",
		"password":password
	}
    
    # To insert a document into a collection
    students.insert_one(user)

    # Automatically decrypts any encrypted fields.
    print('===========================================================')
    print('Decrypted document: ')
    pprint.pprint(students.find_one())
    print('===========================================================')
    unencrypted_coll = MongoClient().SPLNEncrypted.students
    print('Encrypted document: ')
    pprint.pprint(unencrypted_coll.find_one())


    # Cleanup resources.
    client_encryption.close()
    client.close()

if __name__ == "__main__":
    main()