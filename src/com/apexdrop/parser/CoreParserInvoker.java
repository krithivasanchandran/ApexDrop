package com.apexdrop.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.jsoup.nodes.Document;

import com.apexdrop.entity.extractor.EmailExtractor;
import com.apexdrop.entity.extractor.PhoneNumberExtractor;

/*
 * We need support for infinite loop redirects like blackhawknetwork.com - 301 / 302 errors.
 */

public class CoreParserInvoker {

	final static Queue<String> queueA = new LinkedList<String>();
	final static Queue<String> failedPingTestUrls = new LinkedList<String>();
	final static Set<String> duplicateValidator = new HashSet<String>();
	final static Map<String, String> langResult = new HashMap<String, String>();

	public static void main(String args[]) throws IOException {

		final String root = "http://bespokeluggage.com";

		System.out.println(extractSimilar(root));

		String matchingDomainnames = extractSimilar(root).equals("nomatch") ? "null" : extractSimilar(root);

		if (matchingDomainnames == null)
			return;

		queueA.add(root);
		LinkExtractor extractor = new LinkExtractor();
		ExtractorCore core = new ExtractorCore();

		while (queueA.size() > 0) {

			String head = queueA.poll();

			queueA.stream().forEach((q) -> {
				System.out.println("Elements in the Queue -----------> " + q);
			});
			
			if(head.equals(root) || head.contains("contact") || 
					head.contains("about") || head.contains("location")){
				
				try {
					if (!HTTPCore.pingTest(head)) {
						failedPingTestUrls.add(head);
						System.out.println(" Added failed URL's to the queue");
						continue;
					}
				} catch (IOException io) {
					System.out.println(io.getMessage() + " Added failed URL's to the queue");
				}

				Document document = (core.extractDocument(head) != null) ? core.extractDocument(head) : null;

				if (document != null) {
					extractor.extractOutgoingLinks(document, matchingDomainnames, root, duplicateValidator, queueA);

					String body = document.text();
					PhoneNumberExtractor.phoneNumberFinder(body, head);
					EmailExtractor.EmailFinder(body,head);
				}
			}
		}
		
		langResult.keySet().forEach(key -> System.out.println(key + "->" + langResult.get(key)));
		
		failedPingTestUrls.stream().forEach((o) -> System.out.println(" Failed URLS are :: ---> "+ o));
	}

	private static String extractSimilar(String url) {
		return url.startsWith("http://") ? url.split("http://")[1]
				: (url.startsWith("https://") ? url.split("https://")[1] : "nomatch");
	}
}