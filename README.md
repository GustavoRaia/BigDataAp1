<hr>

# Sistema de Cadastro de Clientes e Endereços

## Requisições

Com o código pronto e a utilização do _Postman_, iremos enviar requisições à API para inserir os dados no Banco.
Como auxílio, utilizei o site [4devs.com](https://www.4devs.com.br) para gerar dados de CPF e CEP.
  
### Cliente

#### Cadastro de um Cliente

- Tipo: `POST`
- Caminho : `http://localhost:8080/cliente`

**Caso 1 : Cliente com 1 endereço.**
```json
{
	"nome": "Nome Teste 1",
	"email": "teste@gmail.com",
	"cpf": "181.135.490-48",
	"dataNascimento": "1999-01-01",
	"telefone": "(21) 99015-2760",
	"enderecos":
	[
	{
		"rua": "Rua Teste 1",
		"numero": "1231",
		"bairro": "Bairro Teste 1",
		"cidade": "Cidade Teste 1",
		"estado": "PI",
		"cep": "64011-565"
	}
	]
}
```

**Caso 2: Cliente com múltiplos endereços.**
```json
{
	"nome": "Nome Teste 2",
	"email": "teste2@gmail.com",
	"cpf": "233.500.120-51",
	"dataNascimento": "1999-03-03",
	"telefone": "(11) 99127-6139",
	"enderecos": [
	{
		"rua": "Rua Teste 2",
		"numero": "1232",
		"bairro": "Bairro Teste 2",
		"cidade": "Cidade Teste 2",
		"estado": "ES",
		"cep": "29152-271"
	},
	{
		"rua": "Rua Teste 3 ",
		"numero": "1233",
		"bairro": "Bairro Teste 3",
		"cidade": "Cidade Teste 3",
		"estado": "MG",
		"cep": "37900-193"
	}
]
}
```

**Caso 3: Cliente sem nenhum endereço**
```json
{
	"nome": "Nome Teste 3",
	"email": "teste3@gmail.com",
	"cpf": "207.493.250-20",
	"dataNascimento": "1999-04-04",
	"telefone": "(55) 99237-4138",
	"enderecos": []
}
```

#### Buscar todos os Clientes

- Tipo: `GET`
- Caminho: `http://localhost:8080/cliente` 
- Retorno:
```json
[
	{
		"id": 1,
		"nome": "Nome Teste 1",
		"email": "teste@gmail.com",
		"cpf": "181.135.490-48",
		"dataNascimento": "1999-01-01",
		"telefone": "(21) 99015-2760",
		"enderecos": [
		{
			"id": 1,
			"rua": "Rua Teste 1",
			"numero": "1231",
			"bairro": "Bairro Teste 1",
			"cidade": "Cidade Teste 1",
			"estado": "PI",
			"cep": "64011-565"
		}
		]
	},
	{
		"id": 2,
		"nome": "Nome Teste 2",
		"email": "teste2@gmail.com",
		"cpf": "233.500.120-51",
		"dataNascimento": "1999-03-03",
		"telefone": "(11) 99127-6139",
		"enderecos": [
		{
			"id": 2,
			"rua": "Rua Teste 2",
			"numero": "1232",
			"bairro": "Bairro Teste 2",
			"cidade": "Cidade Teste 2",
			"estado": "ES",
			"cep": "29152-271"
		},
		{
			"id": 3,
			"rua": "Rua Teste 3 ",
			"numero": "1233",
			"bairro": "Bairro Teste 3",
			"cidade": "Cidade Teste 3",
			"estado": "MG",
			"cep": "37900-193"
		}
		]
	},
	{
		"id": 3,
		"nome": "Nome Teste 3",
		"email": "teste3@gmail.com",
		"cpf": "207.493.250-20",
		"dataNascimento": "1999-04-04",
		"telefone": "(55) 99237-4138",
		"enderecos": []
	}
]
```

#### Atualização de um Cliente

Realiza a alteração de dados do cliente. Pelas regras de negócio só é possível alterar os campos de nome, email e telefone.

- Tipo: `PUT`
- Caminho: `http://localhost:8080/cliente/{id}`

**OBS: O Endereço não será atualizado nessa requisição.**

```json
{
	"nome": "Nome Teste 1 Atualizado",
	"email": "testeatualizado@gmail.com",
	"cpf": "181.135.490-48",
	"dataNascimento": "1999-01-01",
	"telefone": "(21) 99842-8911",
	"enderecos": [
	{
		"rua": "Rua Teste 4",
		"numero": "12344",
		"bairro": "Bairro Teste 4",
		"cidade": "Cidade Teste 4",
		"estado": "SE",
		"cep": "49037-219"
	}
	]
}
```

**Ou**

```json
{
	"nome": "Nome Teste 1 Atualizado",
	"email": "testeatualizado@gmail.com",
	"cpf": "181.135.490-48",
	"dataNascimento": "1999-01-01",
	"telefone": "(21) 99842-8911",
	"enderecos": []
}
```
#### Buscar um Cliente por ID

- Tipo: `GET`
- Caminho: `http://localhost:8080/cliente/{id}`
- Retorno:
```json
{
	"id": 3,
	"nome": "Nome Teste 3",
	"email": "teste3@gmail.com",
	"cpf": "207.493.250-20",
	"dataNascimento": "1999-04-04",
	"telefone": "(55) 99237-4138",
	"enderecos": []
}
```

#### Buscar os Endereços de um Cliente

- Tipo: `PUT`
- Caminho: `http://localhost:8080/{id}/endereco/{idEndereco}`
- Retorno:
```json
[
{
	"id": 2,
	"rua": "Rua Teste 2",
	"numero": "1232",
	"bairro": "Bairro Teste 2",
	"cidade": "Cidade Teste 2",
	"estado": "ES",
	"cep": "29152-271"
},
{
	"id": 3,
	"rua": "Rua Teste 3 ",
	"numero": "1233",
	"bairro": "Bairro Teste 3",
	"cidade": "Cidade Teste 3",
	"estado": "MG",
	"cep": "37900-193"
}
]
```

#### Exclusão de um Cliente

Realiza a exclusão de todos os dados de um Cliente do Banco. Inclusive de seus endereços cadastrados.

- Tipo: `DELETE`
- Caminho: `http://localhost:8080/cliente/{id}`
- Retorno:
```json
Cliente deletado com sucesso
```
### <hr>

### Endereço

#### Cadastro de um Endereço

- Tipo: `POST`
- Caminho: `http://localhost:8080/endereco/{id_cliente}`

**OBS: Os valores de {id_cliente} do Caminho e "cliente_id" do JSON devem ser iguais, para realizar a inserção de um novo endereço de maneira correta.**

```json
{
	"rua": "Rua Teste 4",
	"numero": "1234",
	"bairro": "Bairro Teste 4",
	"cidade": "Cidade Teste 4",
	"estado": "SE",
	"cep": "49037-219",
	"cliente_id": 3
}
```
#### Buscar todos os Endereços

- Tipo: `GET`
- Caminho: `http://localhost:8080/endereco`
- Retorno:
```json
[
{
	"id": 1,
	"rua": "Rua Teste 1",
	"numero": "1231",
	"bairro": "Bairro Teste 1",
	"cidade": "Cidade Teste 1",
	"estado": "PI",
	"cep": "64011-565"
},
{
	"id": 2,
	"rua": "Rua Teste 2",
	"numero": "1232",
	"bairro": "Bairro Teste 2",
	"cidade": "Cidade Teste 2",
	"estado": "ES",
	"cep": "29152-271"
},
{
	"id": 3,
	"rua": "Rua Teste 3 ",
	"numero": "1233",
	"bairro": "Bairro Teste 3",
	"cidade": "Cidade Teste 3",
	"estado": "MG",
	"cep": "37900-193"
},
{
	"id": 4,
	"rua": "Rua Teste 4",
	"numero": "12344",
	"bairro": "Bairro Teste 4",
	"cidade": "Cidade Teste 4",
	"estado": "SE",
	"cep": "49037-219"
}
]
```

#### Atualização de um Endereço

- Tipo: `PUT`
- Caminho: `http://localhost:8080/endereco/{id_endereco}`

```json
{
	"rua": "Rua Teste 4 Atualizado",
	"numero": "12344",
	"bairro": "Bairro Teste 4 Atualizado",
	"cidade": "Cidade Teste 4 Atualizado",
	"estado": "MA",
	"cep": "65044-444"
}
```
#### Exclusão de um Endereço

- Tipo: `DELETE`
- Caminho: `http://localhost:8080/endereco/{id_endereco}`
- Retorno:
```json
Endereço deletado com sucesso
```

#### Busca de um Endereço por ID

- Tipo: `GET`
- Caminho: `http://localhost:8080/endereco/{id}`
- Retorno:
```json
{
	"id": 1,
	"rua": "Rua Teste 1",
	"numero": "1231",
	"bairro": "Bairro Teste 1",
	"cidade": "Cidade Teste 1",
	"estado": "PI",
	"cep": "64011-565"
}
```
### <hr>

## Workflow de Requisições

Para avaliar o projeto e validar as alterações que serão feitas no banco de dados, precisamos enviar requisições via *Postman*. Abaixo segue a ordem de requisições para facilitar a compreensão do sistema.

1. Criar Cliente
	Insere 3 Clientes no Banco
	1. Com 1 Endereço.
	2. Com 2 Endereços.
	3. Sem Endereço.
2. Get All Clientes
	Exibe que os Dados foram salvos no Banco.
3. Criar Endereço
	Insere um Endereço para o Cliente 3.
4. Buscar Cliente
	Exibe um Cliente salvo no Banco (busca por id) - C3.
5. Get All Endereços
	Exibe todos os Endereços salvos no Banco.
6. Buscar Endereços Cliente
	Exibe os Endereços do Cliente 2.
7. Alterar Cliente
	Altera os Dados do Cliente 1.
8. Alterar Endereço
	Altera o Endereço do Cliente 3.
9. Buscar Endereço
	Busca o Endereço recém alterado.
10. Deletar Endereço
	Apaga o segundo endereço do Cliente 2 do Banco.
11. Deletar Cliente
	Apaga o Cliente 3 do Banco.
12. Get All Clientes
	Exibe o Resultado Final 
	1. Cliente 1 com Dados Alterados.
	2. Cliente 2 apenas com 1 Endereço.
	3. Cliente 3 Deletado.
