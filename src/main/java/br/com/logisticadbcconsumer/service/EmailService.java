package br.com.logisticadbcconsumer.service;

import br.com.logisticadbcconsumer.dto.*;
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
        } catch (MessagingException | IOException | TemplateException e) {
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

    public void enviarEmailViagem(ViagemCriadaDTO viagemCriadaDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(viagemCriadaDTO.getEmail());
            mimeMessageHelper.setSubject("Viagem Criada");

            String mensagem = "Foi atribuido a você uma nova viagem. Seguem os dados da viagem: ";

            mimeMessageHelper.setText(getViagemTemplate(viagemCriadaDTO, mensagem), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException  e) {
            log.error("Error enviarEmailViagem");
        }
    }

    private String getViagemTemplate(ViagemCriadaDTO viagemCriadaDTO, String mensagem)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", viagemCriadaDTO.getNome());
        dados.put("mensagem", mensagem);
        dados.put("partidaRota", viagemCriadaDTO.getPartidaRota());
        dados.put("inicioViagenm", viagemCriadaDTO.getInicioViagem());
        dados.put("destinoRota", viagemCriadaDTO.getDestinoRota());
        dados.put("fimViagem", viagemCriadaDTO.getFimViagem());
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-viagem.ftl");

        //html
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public void enviarEmailAdminPossiveisClientes(String listaDados) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(from);
            mimeMessageHelper.setSubject("Possíveis Clientes");

            mimeMessageHelper.setText(getAdminPossiveisClientesTemplate(listaDados), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException  e) {
            log.error("Error enviarEmailAdminPossiveisClientes");
        }
    }

    private String getAdminPossiveisClientesTemplate(String listaDados)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", "Administrador");
        dados.put("listaDados", listaDados);

        Template template = fmConfiguration.getTemplate("email-template-admin-possiveis-clientes.ftl");

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public void enviarEmailLogPorDia(LogPorDiaDTO logPorDiaDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo("lucaspalves8@gmail.com"); //TODO Email definido somente para testar - trocar depois
            mimeMessageHelper.setSubject("Logs do dia");

            mimeMessageHelper.setText(getLogPorDiaTemplate(logPorDiaDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException  e) {
            log.error("Error enviarEmailLogPorDia");
        }
    }

    private String getLogPorDiaTemplate(LogPorDiaDTO logPorDiaDTO)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("logPorDia", logPorDiaDTO.getListDTo());
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-log-por-dia.ftl");

        //html
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}



