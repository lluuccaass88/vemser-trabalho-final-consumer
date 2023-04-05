package br.com.logisticadbcconsumer.service;

import br.com.logisticadbcconsumer.dto.PossiveisClientesDTO;
import br.com.logisticadbcconsumer.dto.UsuarioBoasVindasDTO;
import br.com.logisticadbcconsumer.dto.UsuarioRecuperaSenhaDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;
    private final Configuration fmConfiguration;
    private final JavaMailSender emailSender;
    private static String NOME_LOG = "TruckLog";
    private static String EMAIL_LOG = "trucklog@email.com";

    public void enviarEmailPossivelCliente(PossiveisClientesDTO possiveisClientesDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(possiveisClientesDTO.getEmail());
            mimeMessageHelper.setSubject("Contato com o clinte");

            mimeMessageHelper.setText(geEmailPossivelClienteTemplate(possiveisClientesDTO.getEmail(),
                    possiveisClientesDTO.getNome()), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Error enviarEmailPossivelCliente");
        }
    }

    private String geEmailPossivelClienteTemplate(String email, String nome)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("usuarioNome", nome);
        dados.put("usuarioEmail", email);
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-email-possivel-cliente.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void enviarEmailBoasVindas(UsuarioBoasVindasDTO usuarioBoasVindasDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioBoasVindasDTO.getEmail());
            mimeMessageHelper.setSubject("Bem vindo ao " + NOME_LOG);
            mimeMessageHelper.setText(getBoasVindasTemplate(usuarioBoasVindasDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException | NullPointerException e) {
            log.error("Error enviarEmailPossivelCliente");
        }
    }

    private String getBoasVindasTemplate(UsuarioBoasVindasDTO usuarioBoasVindasDTO)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", usuarioBoasVindasDTO.getNome());
        dados.put("loginUsuario", usuarioBoasVindasDTO.getLogin());
        dados.put("cargoUsuario", usuarioBoasVindasDTO.getNomeCargo());
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-boas-vindas-usuario.ftl");

        //html
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public void enviarEmailRecuperarSenha(UsuarioRecuperaSenhaDTO usuarioRecuperaSenhaDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioRecuperaSenhaDTO.getEmail());
            mimeMessageHelper.setSubject("Recuperação de senha");

            mimeMessageHelper.setText(geRecuperarSenhaTemplate(usuarioRecuperaSenhaDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Error enviarEmailRecuperarSenha");
        }
    }

    private String geRecuperarSenhaTemplate(UsuarioRecuperaSenhaDTO usuarioRecuperaSenhaDTO)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("senhaTemporaria", usuarioRecuperaSenhaDTO.getSenha());
        dados.put("usuarioNome", usuarioRecuperaSenhaDTO.getNome());
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-recupera-senha.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    /*public void enviarEmailViagem(RotaEntity rota, ViagemEntity viagem, UsuarioEntity motorista) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(motorista.getEmail());
            mimeMessageHelper.setSubject("Viagem Criada");

            String mensagem = "Foi atribuido a você uma nova viagem. Seguem os dados da viagem: ";

            mimeMessageHelper.setText(getViagemTemplate(rota, viagem, motorista.getNome(), mensagem), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException |  NullPointerException e) {
            throw new RegraDeNegocioException("Erro ao enviar email.");
        }
    }

    private String getViagemTemplate(RotaEntity rota, ViagemEntity viagem, String nomeUsuario,String mensagem)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", nomeUsuario);
        dados.put("mensagem", mensagem);
        dados.put("rota", rota);
        dados.put("viagem", viagem);
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-viagem.ftl");

        //html
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }*/
}



