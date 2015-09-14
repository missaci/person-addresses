CRUD de Endereço de Pessoas
==================================
Implementação de CRUD básico de endereços. Feito para consumir o serviço de busca de CEP existente em: https://github.com/missaci/addressesTest

Este aplicativo foi desenvolvido usando spring-boot para facilitar o uso da aplicação tanto de forma standalone quanto com deploy em servidores de aplicação.

O desenvolvimento foi realizado baseado nas técnicas de TDD e DDD e com foco em simplicidade.

##Sobre os componentes usados
Para construção dessa aplicação, as seguintes tecnologias foram usadas:

- Spring-boot;
- Spring-MVC;
- Spring-data-jpa;
- Hibernate 4;
- JPA 2.1;
- Java 8;
- H2;
- Maven.

##Utilização em produção
Esse aplicativo foi desenvolvido apenas para fins de teste, mas pode ser facilmente expandido para uso em produção a partir do uso de perfis no Spring ou no próprio maven. 

##Utilização
Por default a aplicação é inicializada na porta **9000**. A porta pode ser alterada no arquivo **application.yml**.

Você pode checar se a aplicação está rodando, acessando a url:
http://localhost:9000

a aplicação deverá exibir na tela a mensagem: "Up and running!"

###O CRUD

#### Inserção

Para inserção, utilize o método POST do HTTP na URL: http://localhost:9000/personAddresses .
O conteúdo da requisição deve ser semelhante ao abaixo:

    {
    	"cep":"01001-001", 
    	"street":"Rua D", 
    	"number":"2", 
    	"city":"São Paulo", 
    	"state": "SP", 
    	"neighborhood":"Sé",
    	"complement": "próximo à praça"
    }

Bairro (Neighborhood) e Complemento (complement) são opcionais.

#### Atualização
Para atualização, utilize o método PUT do HTTP na URL: http://localhost:9000/personAddresses .
O conteúdo da requisição deve ser semelhante ao abaixo:

    {
    	"id": 1
    	"cep":"01001-001", 
    	"street":"Rua D", 
    	"number":"2", 
    	"city":"São Paulo", 
    	"state": "SP", 
    	"neighborhood":"Sé",
    	"complement": "próximo à praça"
    }

Bairro (Neighborhood) e Complemento (complement) são opcionais.

#### Remoção
Para atualização, utilize o método DELETE do HTTP na URL: http://localhost:9000/personAddresses/{id}, onde {id} deve ser substituído pelo id do elemento a ser deletado.
Não há conteúdo de requisição para esse caso.

#### Obtenção
Não há listagem nesse serviço, apenas obtenção de um endereço por id. Para essa função, use o método GET do HTTP na URL: http://localhost:9000/personAddresses/{id}, onde {id} deve ser substituído pelo id do elemento a ser obtido.
Não há conteúdo de requisição para esse caso.

#### Falhas
Em caso de falhas, o servidor irá retornar o status 412 (Pré-condition failed) para erros gerados pelo usuário (erros de validação, campos não preenchidos e afins) ou o status 500 para erros internos do servidor.
O status será acompanhado de uma mensagem semelhante a abaixo:


	{
		"errorMessage": "CEP inválido"
	}
