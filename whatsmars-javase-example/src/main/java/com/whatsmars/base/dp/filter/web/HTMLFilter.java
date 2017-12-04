package com.whatsmars.base.dp.filter.web;

public class HTMLFilter implements Filter {

	@Override
	public void doFilter(Request request, Response response, FilterChain fc) {
		//process the html tag <>
		request.requestStr = request.requestStr.replace('<', '[')
				   .replace('>', ']') + "---HTMLFilter()";
		fc.doFilter(request, response, fc);
		response.responseStr += "---HTMLFilter()";
	}

}
