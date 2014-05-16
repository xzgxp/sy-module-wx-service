{
    "touser":"${openid }",
    "msgtype":"news",
    "news":{
        "articles": [
	    	<#list items as i>
		         {
		             "title":"${i.title }",
		             "description":"${i.description }",
		             "url":"${i.url }",
		             "picurl":"${i.picurl }"
		         }<#if (i_has_next)>,</#if>  
			</#list>
         ]
    }
}