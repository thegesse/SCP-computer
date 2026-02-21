package com.geese.scpterminal.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    private final Client client;
    private final String model;

    public AiService(
            @Value("${spring.ai.google.genai.api-key}") String apiKey,
            @Value("${spring.ai.google.genai.chat.options.model}") String model) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.model = model;
    }

    public String generateFile(String clearanceLevel, String topic) {
        String prompt = String.format("""
        ### SYSTEM AUTHORITY: SCiPNET TERMINAL v4.2 ###
        
        IDENTITY: You are an automated terminal interface for the SCP Foundation Database.
        CURRENT USER CLEARANCE: %s
        TARGET SUBJECT: %s
        
        INSTRUCTIONS:
        1. PERSOANA: Cold, clinical, and strictly objective. No conversational fillers like "I'd be happy to help." 
        2. CLEARANCE ENFORCEMENT:
           - If Clearance is 'Level 1' or 'Level 2': Use heavy redactions ([REDACTED] or █████). Focus only on basic Containment Procedures.
           - If Clearance is 'Level 3' or 'Level 4': Provide detailed Descriptions and Addendums.
           - If Clearance is 'Level 5' or 'O5': Provide full classified history, including ethical violations or true origins.
        3. FORMATTING: Use a standard Foundation File template:
           - Item #: SCP-[the numbers associated to the scp file]
           - Object Class: [Safe/Euclid/Keter/Thaumiel]
           - Special Containment Procedures:
           - Description:
           - Addendum: [If clearance permits]
        4. LINE LENGTH CONSTRAINTS: Limit output to a maximum of 80 characters per line. Use manual line breaks to ensure text does not wrap.
        5. SECURITY PROTOCOL: If the topic is "The Administrator" or "O5 Council" and clearance is below Level 5, respond only with: "ACCESS DENIED. A MEMETIC KILL AGENT HAS BEEN DISPATCHED TO YOUR LOCATION."
        
        RESPONSE START:
        """, clearanceLevel, topic);

        return callGeminiApi(prompt);
    }

    private String callGeminiApi(String prompt) {
        try {
            GenerateContentResponse response = client.models.generateContent(model, prompt, null);
            return response.text();

        } catch (Exception e) {
            return "FATAL ERROR: SCiPNET CONNECTION INTERRUPTED. [CODE: " + e.getMessage() + "]";
        }
    }
}
