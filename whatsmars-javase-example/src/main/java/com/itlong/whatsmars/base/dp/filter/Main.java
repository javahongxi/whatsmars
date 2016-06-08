package com.itlong.whatsmars.base.dp.filter;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String msg = "大家好:)，<script>敏感，被就业，网络授课没感觉，因为看不见大家伙儿";
		MsgProcessor mp = new MsgProcessor();
		mp.setMsg(msg);
		FilterChain fc = new FilterChain();
		fc.addFilter(new HTMLFilter())
		  .addFilter(new SensitiveFilter())
		  ;
		FilterChain fc2 = new FilterChain();
		fc2.addFilter(new FaceFilter());
		
		fc.addFilter(fc2);
		mp.setFc(fc);
		String result = mp.process();
		System.out.println(result);
	}

}
